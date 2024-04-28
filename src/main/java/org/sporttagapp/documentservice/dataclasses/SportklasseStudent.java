package org.sporttagapp.documentservice.dataclasses;

import java.util.List;

public record SportklasseStudent(String klassenname, String sportlehrerkuerzel, List<Student> students) {
}
