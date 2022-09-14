UPDATE organization
    set contact_text = "¡Gracias por contactarte con Somos Más! A la brevedad vamos a estar respondiendo a tu consulta.",
        updated_at = current_timestamp()
    where organization_id=1;