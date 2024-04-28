package org.sporttagapp.documentservice.dataclasses;

import java.sql.Date;

public record Student(Long id, String vorname, String nachname, String geschlecht, String klasse,
                         Date geburtstag, Long sportklasseId) { }
