package Chen.Patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HiDefPowerPatch {
    @SpirePatch(
            clz= AbstractPower.class,
            method=SpirePatch.CLASS
    )
    public static class HiDefImage
    {
        public static SpireField<Texture> img84 = new SpireField<>(()->null);
    }
}
