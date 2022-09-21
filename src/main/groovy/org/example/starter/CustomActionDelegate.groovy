package org.example.starter

import com.itextpdf.html2pdf.HtmlConverter
import com.netgrif.application.engine.petrinet.domain.dataset.FileField
import com.netgrif.application.engine.petrinet.domain.dataset.FileFieldValue
import com.netgrif.application.engine.petrinet.domain.dataset.TextField
import com.netgrif.application.engine.petrinet.domain.dataset.logic.action.ActionDelegate
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths

@Component
class CustomActionDelegate extends ActionDelegate {

    void htmlToPdf(FileField fileField, TextField textField, String fileName = "output") {
        File template = new File("src/main/resources/html_template/default.html")
        String data = new String(Files.readAllBytes(Paths.get(template.path)))
        data = data.replace("{{text}}", textField.value)
        File file = new File("storage/" + fileName + ".pdf")
        FileOutputStream fileOutputStream = new FileOutputStream(file)
        HtmlConverter.convertToPdf(data, fileOutputStream)
        fileOutputStream.close()
        change fileField value { new FileFieldValue(file.name, file.path) }
    }

}