CREATE TABLE transactions (
                              transaction_id UUID PRIMARY KEY,
                              account_id UUID NOT NULL,
                              operation_type_id INT NOT NULL,
                              amount NUMERIC(19, 2) NOT NULL,
                              event_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);