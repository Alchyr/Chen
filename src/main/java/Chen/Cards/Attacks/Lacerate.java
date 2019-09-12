package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Chen.ChenMod.makeID;
import static Chen.Patches.TwoFormFields.getForm;

public class Lacerate extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Lacerate",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 6;
    private final static int UPG_DAMAGE = 3;

    private final static int HEMORRHAGE = 1;

    public Lacerate()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(HEMORRHAGE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }



        if (m != null)
        {
            for (int i = 0; i < this.damage; i++)
            {
                m.damageFlash = true;
                m.damageFlashFrames = 2;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new FlashAtkImgEffect(m.hb.cX + MathUtils.random(-20.0f, 20.0f) * Settings.scale, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_VERTICAL, true)));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_DAGGER_3", 0.05f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.magicNumber), this.magicNumber, true));
            }
        }
    }
}