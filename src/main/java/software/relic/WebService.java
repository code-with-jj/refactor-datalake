package software.relic;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.Closeable;

public class WebService implements Closeable {
	private final Javalin service;
	private final Datalake datalake;

	public WebService() {
		this.service = Javalin.create().start(7000);
		this.datalake = new Datalake();
	}

	public void init() {
		service.get("/sales/{id}", this::retrieveSale);
	}

	private void retrieveSale(Context ctx) {
		String id = ctx.pathParam("id");

		try {
			String json = datalake.get(id);
			if (json != null) {
				ctx.result(json);
			} else {
				ctx.status(404).result("Sale not found: " + id);
			}
		} catch (Exception e) {
			ctx.status(500).result("Error retrieving sale: " + e.getMessage());
		}
	}


	@Override
	public void close() {
		datalake.close();
	}
}
