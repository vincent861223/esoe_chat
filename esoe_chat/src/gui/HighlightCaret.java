package gui;

import java.awt.Color;

import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

class HighlightCaret extends DefaultCaret {

    private static final Highlighter.HighlightPainter unfocusedPainter =
            new DefaultHighlighter.DefaultHighlightPainter(new Color(230, 230, 210));
    private static final long serialVersionUID = 1L;
    private boolean isFocused;

    @Override
    protected Highlighter.HighlightPainter getSelectionPainter() {
        return isFocused ? super.getSelectionPainter() : unfocusedPainter;
    }

    @Override
    public void setSelectionVisible(boolean hasFocus) {
        if (hasFocus != isFocused) {
            isFocused = hasFocus;
            super.setSelectionVisible(false);
            super.setSelectionVisible(true);
        }
    }
}