ALTER TABLE inventarization ALTER COLUMN status TYPE INTEGER USING (
    CASE status
        WHEN 'CREATED' THEN 1
        WHEN 'IN_PROGRESS' THEN 2
        WHEN 'COMPLETED' THEN 3
        ELSE 1
    END
);