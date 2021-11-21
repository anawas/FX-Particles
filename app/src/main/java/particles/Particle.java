package particles;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Particle {

    private final double MAX_ATTRACTION_DISTANCE = 350;
    private final double MIN_ATTRACTION_DISTANCE = 1.0;
    private final double FORCE_CONSTANT = 90;

    double x;
    double y;

    double originalX;
    double originalY;

    Color color;

    boolean suckedIn = false;

    public Particle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;

        this.color = color;
    }


    public void update(Point2D cursorPos) {
        var distance = cursorPos.distance(x, y);
        if (suckedIn) return;
        if (distance > MAX_ATTRACTION_DISTANCE) {
            x = originalX;
            y = originalY;
        } else if (distance < MIN_ATTRACTION_DISTANCE) {
            // Particles inside min. attraction distance are marked to be deleted
            x = cursorPos.getX();
            y = cursorPos.getY();
            suckedIn = true;
        } else {
            var vector = cursorPos.subtract(x, y);  
            var scaledLength = FORCE_CONSTANT * 1 / distance;
            if (scaledLength >= 100) scaledLength = 0;
            vector = vector.normalize().multiply(scaledLength);

            //x = originalX + vector.getX();
            x += vector.getX();
            //y = originalY + vector.getY();
            y += vector.getY();
        }
    }

    public boolean canBeRemoved() {
        return suckedIn;
    }
    
}
