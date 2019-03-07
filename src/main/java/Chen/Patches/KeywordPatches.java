package Chen.Patches;

import Chen.Abstracts.ShiftChenCard;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import javassist.CtBehavior;

import java.util.ArrayList;

import static Chen.ChenMod.logger;

@SpirePatch(
        clz = TipHelper.class,
        method = "renderKeywords"
)
public class KeywordPatches {
    public static String SHIFT_KEYWORD = ""; //These are updated in main mod class during keyword initialization
    public static String SHIFT_WORD = "";
    public static final String FOCUS_KEYWORD = GameDictionary.FOCUS.NAMES[0].toLowerCase();
    public static final String ALT_FOCUS_KEYWORD = "chen:altfocus";

    private static final Color BASE_COLOR;
    private static final float SHADOW_DIST_Y;
    private static final float SHADOW_DIST_X;
    private static final float BOX_EDGE_H;
    private static final float BOX_BODY_H;
    private static final float BOX_W;
    private static final float TEXT_OFFSET_X;
    private static final float HEADER_OFFSET_Y;
    private static final float BODY_OFFSET_Y;
    private static final float BODY_TEXT_WIDTH;
    private static final float TIP_DESC_LINE_SPACING;


    @SpireInsertPatch(
        rloc=0,
        localvars={"card", "y"}
    )
    public static void ModifyKeywords(float x, float stupidDumbUselessYThatIsntByRef, SpriteBatch sb, ArrayList<String> keywords,
                                     AbstractCard card, @ByRef float[] y)
    {
        if (keywords.contains(FOCUS_KEYWORD) && card.color == CardColorEnum.CHEN_COLOR)
        {
            keywords.remove(FOCUS_KEYWORD);
            keywords.add(ALT_FOCUS_KEYWORD);
        }
        if (keywords.contains(SHIFT_KEYWORD) && card.tags.contains(CardTagsEnum.CHEN_SHIFT_CARD))
        {
            String keywordTitle = SHIFT_WORD + " - " + ((ShiftChenCard) card).getShiftName();
            String keywordText = ((ShiftChenCard) card).getShiftDescription();
            float textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, keywordText, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;


            float useY = y[0];

            //This is at the start of method, have to adjust y a bit
            if (keywords.size() >= 5) //Even after removal, keyword amount > 4
            {
                y[0] += 62.0F * Settings.scale; //adjust the actual y slightly. After addition in TipHelper, it will match useY here.
                useY += (float)(keywords.size() - 1) * 62.0F * Settings.scale; //Adjust this to where it would be rendered if rendered normally
            }
            else if (keywords.size() == 4) //After removal, it won't be adjusted
            {
                useY += textHeight + BOX_EDGE_H * 3.15F; //make space for the box above normal keywords
                y[0] += textHeight + BOX_EDGE_H * 3.15F;
            }


            renderBox(sb, keywordTitle, keywordText, textHeight, x, useY);
            y[0] -= textHeight + BOX_EDGE_H * 3.15F;

            keywords.remove(SHIFT_KEYWORD);
        }
    }


    @SpirePostfixPatch
    public static void ReturnShiftKeyword(float x, float y, SpriteBatch sb, ArrayList<String> keywords)
    {
        AbstractCard card = (AbstractCard)ReflectionHacks.getPrivateStatic(TipHelper.class, "card");
        if (card instanceof ShiftChenCard && !keywords.contains(SHIFT_KEYWORD))
        {
            keywords.add(SHIFT_KEYWORD);
        }
    }


    private static void renderBox(SpriteBatch sb, String title, String Text, float textHeight, float x, float y) {
        sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
        sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - textHeight - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, textHeight + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - textHeight - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BODY, x, y - textHeight - BOX_EDGE_H, BOX_W, textHeight + BOX_EDGE_H);
        sb.draw(ImageMaster.KEYWORD_BOT, x, y - textHeight - BOX_BODY_H, BOX_W, BOX_EDGE_H);

        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);

        FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, Text, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
    }


    static {
        BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
        SHADOW_DIST_Y = 14.0F * Settings.scale;
        SHADOW_DIST_X = 9.0F * Settings.scale;
        BOX_EDGE_H = 32.0F * Settings.scale;
        BOX_BODY_H = 64.0F * Settings.scale;
        BOX_W = 320.0F * Settings.scale;
        TEXT_OFFSET_X = 22.0F * Settings.scale;
        HEADER_OFFSET_Y = 12.0F * Settings.scale;
        BODY_OFFSET_Y = -20.0F * Settings.scale;
        BODY_TEXT_WIDTH = 280.0F * Settings.scale;
        TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    }
}
