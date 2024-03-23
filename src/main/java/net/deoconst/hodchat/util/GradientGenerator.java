package net.deoconst.hodchat.util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradientGenerator {
    private final Color[] colors;
    private final int numSteps;

    public GradientGenerator(final String playerName, String... colorHexes) {
        checkHex(colorHexes);
        this.colors = new Color[colorHexes.length];
        for (int i = 0; i < colorHexes.length; ++i) {
            this.colors[i] = Color.decode(colorHexes[i]);
        }
        this.numSteps = playerName.length();
    }
    private boolean checkHex(String... colors){
        final Pattern pattern;
        final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";
        Matcher matcher;
        pattern = Pattern.compile(HEX_PATTERN);
        for (String color : colors) {
            matcher = pattern.matcher(color);
            if (!matcher.matches()) return false;
        }
        return true;
    }
    private Color getColorForFraction(final float[] fractions, final float fraction) {
        for (int i = 0; i < fractions.length - 1; ++i) {
            if (fraction >= fractions[i] && fraction <= fractions[i + 1]) {
                final float t = (fraction - fractions[i]) / (fractions[i + 1] - fractions[i]);
                final int r = (int)(this.colors[i].getRed() + t * (this.colors[i + 1].getRed() - this.colors[i].getRed()));
                final int g = (int)(this.colors[i].getGreen() + t * (this.colors[i + 1].getGreen() - this.colors[i].getGreen()));
                final int b = (int)(this.colors[i].getBlue() + t * (this.colors[i + 1].getBlue() - this.colors[i].getBlue()));
                return new Color(r, g, b);
            }
        }
        return this.colors[this.colors.length - 1];
    }

    private ArrayList<String> minecraftFormat() {
        final float[] fractions = new float[this.colors.length];
        for (int i = 0; i < this.colors.length; ++i) {
            fractions[i] = i / (float)(this.colors.length - 1);
        }
        final ArrayList<String> hex = new ArrayList<>();
        for (int step = 0; step < this.numSteps; ++step) {
            final float t = step / (float)(this.numSteps - 1);
            final Color color = this.getColorForFraction(fractions, t);
            hex.add("§x" + String.format("§%01x", color.getRed() / 16) + String.format("§%01x", color.getRed() % 16) + String.format("§%01x", color.getGreen() / 16) + String.format("§%01x", color.getGreen() % 16) + String.format("§%01x", color.getBlue() / 16) + String.format("§%01x", color.getBlue() % 16));
        }
        return hex;
    }

    public String getNickGradient(final String playerName) {
        final ArrayList<String> formatedGradient = this.minecraftFormat();
        final StringBuilder newNick = new StringBuilder();
        for (int i = 0; i < formatedGradient.size(); ++i) {
            newNick.append(String.format("%s%c", formatedGradient.get(i), playerName.charAt(i)));
        }
        return newNick.toString();
    }
}