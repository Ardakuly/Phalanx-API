CREATE TABLE IF NOT EXISTS inventarization (
    id BIGSERIAL PRIMARY KEY,
    status INTEGER NOT NULL,
    started_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    conducted_by VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    closed_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS inventarization_item (
    id BIGSERIAL PRIMARY KEY,
    inventarization_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    expected_quantity DECIMAL(19, 2) NOT NULL,
    counted_quantity DECIMAL(19, 2),
    final_quantity DECIMAL(19, 2),
    difference DECIMAL(19, 2),
    updated_at TIMESTAMP,
    CONSTRAINT fk_inventarization
      FOREIGN KEY(inventarization_id) 
	  REFERENCES inventarization(id)
	  ON DELETE CASCADE,
    CONSTRAINT fk_product
      FOREIGN KEY(product_id) 
	  REFERENCES product(id)
	  ON DELETE CASCADE
);