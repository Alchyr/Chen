package Chen.Patches;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashPowerEffect;
import javassist.CtBehavior;

public class FlashUseHiDefPatch {
    @SpirePatch(
            clz=FlashPowerEffect.class,
            method=SpirePatch.CLASS
    )
    public static class FlashPowerEffectQualityBoolean
    {
        public static SpireField<Boolean> highQualityImg = new SpireField<>(()->false);
    }


    @SpirePatch(
            clz=FlashPowerEffect.class,
            method=SpirePatch.CONSTRUCTOR
    )
    public static class FlashPowerEffectConstructorPatch
    {
        @SpireInsertPatch (
                locator=Locator.class,
                localvars={"img"}
        )
        public static void Insert(FlashPowerEffect __instance, AbstractPower power, @ByRef(type="com.badlogic.gdx.graphics.Texture") Object[] img)
        {
            Texture HiDefPower = HiDefPowerPatch.HiDefImage.img84.get(power);
            if (HiDefPower != null)
            {
                FlashPowerEffectQualityBoolean.highQualityImg.set(__instance, true);
                img[0] = HiDefPower;
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPower.class, "region128");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=FlashPowerEffect.class,
            method="render"
    )
    public static class FlashPowerEffectRenderPatch
    {
        @SpireInsertPatch (
                locator=Locator.class,
                localvars = {"img", "x", "y", "scale"}
        )
        public static SpireReturn checkImageQualityPatch(FlashPowerEffect __instance, SpriteBatch sb, @ByRef(type="com.badlogic.gdx.graphics.Texture") Object[] img, float x, float y, float scale)
        {
            Boolean hasHighQualityTexture = FlashPowerEffectQualityBoolean.highQualityImg.get(__instance);
            if (hasHighQualityTexture)
            {
                sb.draw((Texture)img[0], x - 42.0F, y - 42.0F, 42.0F, 42.0F, 84.0F, 84.0F, scale * 4.5F, scale * 4.5F, 0.0F, 0, 0, 84, 84, false, false);
                sb.setBlendFunction(770, 771);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
