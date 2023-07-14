package app.resketchware.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class ColorPickerUtil {

    private static final List<Integer> RED = Arrays.asList(0xffffebee, 0xffffcdd2, 0xffef9a9a, 0xffe57373, 0xffef5350, 0xfff44336, 0xffe53935, 0xffd32f2f, 0xffc62828, 0xffb71c1c);
    private static final List<Integer> PINK = Arrays.asList(0xfffce4ec, 0xfff8bbd0, 0xfff48fb1, 0xfff06292, 0xffec407a, 0xffe91e63, 0xffd81b60, 0xffc2185b, 0xffad1457, 0xff880e4f);
    private static final List<Integer> PURPLE = Arrays.asList(0xfff3e5f5, 0xffe1bee7, 0xffce93d8, 0xffba68c8, 0xffab47bc, 0xff9c27b0, 0xff8e24aa, 0xff7b1fa2, 0xff6a1b9a, 0xff4ab9a);
    private static final List<Integer> DEEP_PURPLE = Arrays.asList(0xffede7f6, 0xffd1c4e9, 0xffb39ddb, 0xff9575cd, 0xff7e57c2, 0xff673ab7, 0xff5e35b1, 0xff512da8, 0xff4527a0, 0xff311b92);
    private static final List<Integer> INDIGO = Arrays.asList(0xffe8eaf6, 0xffc5cae9, 0xff9fa8da, 0xff7986cb, 0xff5c6bc0, 0xff3f51b5, 0xff3949ab, 0xff303f9f, 0xff283593, 0xff1a237e);
    private static final List<Integer> BLUE = Arrays.asList(0xffe3f2fd, 0xffbbdefb, 0xff90caf9, 0xff64b5f6, 0xff42a5f5, 0xff2196f3, 0xff1e88e5, 0xff1976d2, 0xff1565c0, 0xff0d47a1);
    private static final List<Integer> LIGHT_BLUE = Arrays.asList(0xffe1f5fe, 0xffb3e5fc, 0xff81d4fa, 0xff4fc3f7, 0xff29b6f6, 0xff03a9f4, 0xff039be5, 0xff0288d1, 0xff0277bd, 0xff01579b);
    private static final List<Integer> CYAN = Arrays.asList(0xffe0f7fa, 0xffb2ebf2, 0xff80deea, 0xff4dd0e1, 0xff26c6da, 0xff00bcd4, 0xff00acc1, 0xff0097a7, 0xff00838f, 0xff006064);
    private static final List<Integer> TEAL = Arrays.asList(0xffe0f2f1, 0xffb2dfdb, 0xff80cbc4, 0xff4db6ac, 0xff26a69a, 0xff009688, 0xff00897b, 0xff00796b, 0xff00695c, 0xff004d40);
    private static final List<Integer> GREEN = Arrays.asList(0xffe8f5e9, 0xffc8e6c9, 0xffa5d6a7, 0xff81c784, 0xff66bb6a, 0xff4caf50, 0xff43a047, 0xff388e3c, 0xff2e7d32, 0xff1b5e20);
    private static final List<Integer> LIGHT_GREEN = Arrays.asList(0xfff1f8e9, 0xffdcedc8, 0xffc5e1a5, 0xffaed581, 0xff9ccc65, 0xff8bc34a, 0xff7cb342, 0xff689f38, 0xff558b2f, 0xff33691e);
    private static final List<Integer> LIME = Arrays.asList(0xfff9fbe7, 0xfff0f4c3, 0xffe6ee9c, 0xffdce775, 0xffd4e157, 0xffcddc39, 0xffc0ca33, 0xffafb42b, 0xff9e9d24, 0xff827717);
    private static final List<Integer> YELLOW = Arrays.asList(0xfffffde7, 0xfffff9c4, 0xfffff59d, 0xfffff176, 0xffffee58, 0xffffeb3b, 0xfffdd835, 0xfffbc02d, 0xfff9a825, 0xfff57f17);
    private static final List<Integer> AMBER = Arrays.asList(0xfffff8e1, 0xffffecb3, 0xffffe082, 0xffffd54f, 0xffffca28, 0xffffc107, 0xffffb300, 0xffffa000, 0xffff8f00, 0xffff6f00);
    private static final List<Integer> ORANGE = Arrays.asList(0xfffff3e0, 0xffffe0b2, 0xffffcc80, 0xffffb74d, 0xffffa726, 0xffff9800, 0xfffb8c00, 0xfff57c00, 0xffef6c00, 0xffe65100);
    private static final List<Integer> DEEP_ORANGE = Arrays.asList(0xfffbe9e7, 0xffffccbc, 0xffffab91, 0xffff8a65, 0xffff7043, 0xffff5722, 0xfff4511e, 0xffe64a19, 0xffd84315, 0xffbf360c);
    private static final List<Integer> BROWN = Arrays.asList(0xffefebe9, 0xffd7ccc8, 0xffbcaaa4, 0xffa1887f, 0xff8d6e63, 0xff795548, 0xff6d4c41, 0xff5d4037, 0xff4e342e, 0xff3e2723);
    private static final List<Integer> GREY = Arrays.asList(0xfffafafa, 0xfff5f5f5, 0xffeeeeee, 0xffe0e0e0, 0xffbdbdbd, 0xff9e9e9e, 0xff757575, 0xff616161, 0xff424242, 0xff212121);
    private static final List<Integer> BLUE_GREY = Arrays.asList(0xffeceff1, 0xffcfd8dc, 0xffb0bec5, 0xff90a4ae, 0xff78909c, 0xff607d8b, 0xff546e7a, 0xff455a64, 0xff37474f, 0xff263238);
    private static final List<Integer> BLACK = Arrays.asList(0xff000000);
    private static final List<Integer> WHITE = Arrays.asList(0xffffffff);

    public static final Map<Integer, List<Integer>> colorsMap = new HashMap<>();

    static {
        colorsMap.put(0, RED);
        colorsMap.put(1, PINK);
        colorsMap.put(2, PURPLE);
        colorsMap.put(3, DEEP_PURPLE);
        colorsMap.put(4, INDIGO);
        colorsMap.put(5, BLUE);
        colorsMap.put(6, LIGHT_BLUE);
        colorsMap.put(7, CYAN);
        colorsMap.put(8, TEAL);
        colorsMap.put(9, GREEN);
        colorsMap.put(10, LIGHT_GREEN);
        colorsMap.put(11, LIME);
        colorsMap.put(12, YELLOW);
        colorsMap.put(13, AMBER);
        colorsMap.put(14, ORANGE);
        colorsMap.put(15, DEEP_ORANGE);
        colorsMap.put(16, BROWN);
        colorsMap.put(17, GREY);
        colorsMap.put(18, BLUE_GREY);
        colorsMap.put(19, BLACK);
        colorsMap.put(20, WHITE);
    }
}