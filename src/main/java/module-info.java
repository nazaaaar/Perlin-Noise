module edu.nazaaaar.perlinnoise {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires annotations;

    exports edu.nazaaaar.perlinnoise.interpretation3D;
    opens edu.nazaaaar.perlinnoise.interpretation3D to javafx.fxml;
    exports utils;
    opens utils to javafx.fxml;
    exports edu.nazaaaar.perlinnoise.heightmap;
    opens edu.nazaaaar.perlinnoise.heightmap to javafx.fxml;
    exports edu.nazaaaar.perlinnoise;
    opens edu.nazaaaar.perlinnoise to javafx.fxml;
}