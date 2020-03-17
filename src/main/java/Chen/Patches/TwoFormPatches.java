package Chen.Patches;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import javassist.CtBehavior;

import static Chen.ChenMod.assetPath;

public class TwoFormPatches {
    private static SpriterAnimation cat = new SpriterAnimation(assetPath("img/Character/Spriter/Cat/cat.scml"));

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "onVictory"
    )
    public static class ResetOnVictory
    {
        @SpirePostfixPatch
        public static void reset(AbstractPlayer __instance)
        {
            TwoFormFields.ShiftDefault();
            TwoFormFields.shiftsThisTurn = 0;
            //__instance.powers.clear();
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "applyStartOfTurnRelics"
    )
    public static class ResetOnTurnStart
    {
        @SpirePrefixPatch
        public static void reset(AbstractPlayer __instance)
        {
            TwoFormFields.shiftsThisTurn = 0;
        }
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "render"
    )
    public static class RenderCat
    {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static SpireReturn renderAlt(AbstractPlayer __instance, SpriteBatch sb)
        {
            if (!TwoFormFields.getForm(__instance))
            {
                cat.setFlip(__instance.flipHorizontal, __instance.flipVertical);
                cat.renderSprite(sb, __instance.drawX + __instance.animX, __instance.drawY + __instance.animY + AbstractDungeon.sceneOffsetY);

                /*if (__instance.damageFlash) {
                    ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
                    --__instance.damageFlashFrames;
                    if (__instance.damageFlashFrames == 0) {
                        __instance.damageFlash = false;
                    }
                }*/

                __instance.hb.render(sb);
                __instance.healthHb.render(sb);
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }


        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "atlas");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
