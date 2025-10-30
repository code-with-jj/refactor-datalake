package software.relic;

import software.amazon.awssdk.annotations.NotNull;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.io.*;
import java.util.stream.Stream;

import static software.amazon.awssdk.regions.Region.US_EAST_1;

public class Datalake implements Closeable {
	private final static String Bucket = "relic-production-mrw-datalake";
	private final S3Client client;

	public Datalake() {
		this.client = s3Client();
	}

	public String get(String id) throws Exception {
		return linesFor(id)
				.filter(line -> line.contains(id))
				.findFirst()
				.orElse(null);
	}

	private Stream<String> linesFor(String id) {
		return linesFor(requestIn(id));
	}

	private Stream<String> linesFor(GetObjectRequest request) {
		return linesFor(client.getObject(request));
	}

	private Stream<String> linesFor(InputStream is) {
		return linesFor(toReader(is)).onClose(()->close(is));
	}

	private Stream<String> linesFor(BufferedReader reader) {
		return reader.lines();
	}

	private GetObjectRequest requestIn(String id) {
		return GetObjectRequest.builder()
				.bucket(Bucket)
				.key(keyIn(id))
				.build();
	}

	private static String keyIn(String id) {
		return id.substring(0, 8) + ".tsv";
	}

	private static BufferedReader toReader(InputStream input) {
		return new BufferedReader(new InputStreamReader(input));
	}

	private static void close(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static S3Client s3Client() {
		return S3Client.builder()
				.region(US_EAST_1)
				.credentialsProvider(credentialsProvider())
				.build();
	}

	@NotNull
	private static AwsCredentialsProvider credentialsProvider() {
		return DefaultCredentialsProvider.builder().build();
	}

	@Override
	public void close() {
		client.close();
	}
}
