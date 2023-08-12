package Chen.Patches;

import Chen.Effects.PoofEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = SpirePatch.CLASS
)
public class TwoFormFields {
    private static final boolean DEFAULT_FORM = true;

    public static SpireField<Boolean> form = new SpireField<>(()->true);

    public static int shiftsThisTurn = 0;



    public static boolean getForm(AbstractPlayer p)
    {
        return form.get(p);
    }
    public static boolean getForm()
    {
        return form.get(AbstractDungeon.player);
    }
    public static void Shift()
    {
        Shift(!form.get(AbstractDungeon.player));
    }
    public static void Shift(boolean form)
    {
        if (TwoFormFields.form.get(AbstractDungeon.player) ^ form)
        {
            TwoFormFields.form.set(AbstractDungeon.player, form);
        }
    }

    public static void ShiftDefault()
    {
        if (TwoFormFields.form.get(AbstractDungeon.player) ^ DEFAULT_FORM)
        {
            AbstractDungeon.effectList.add(new PoofEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
            TwoFormFields.form.set(AbstractDungeon.player, DEFAULT_FORM);
        }
    }
}
