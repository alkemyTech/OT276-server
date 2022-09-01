UPDATE organization
    set contact_text = "texto de contacto",
        updated_at = current_timestamp()
    where organization_id=1;
