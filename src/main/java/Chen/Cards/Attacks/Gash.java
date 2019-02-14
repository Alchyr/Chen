package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Effects.GashEffect;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Cleave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Chen.ChenMod.makeID;

public class Gash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Gash",
            3,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 12;
    private final static int UPG_DAMAGE = 3;

    private final static int DEBUFF = 8;
    private final static int UPG_DEBUFF = 4;

    public Gash()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && ((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        if (m != null)
        {
            //AbstractDungeon.actionManager.addToBottom(new VFXAction(new GashEffect(m.hb.cX, m.hb.cY, Color.RED, Color.BLACK)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY)));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.magicNumber), this.magicNumber));
    }
}