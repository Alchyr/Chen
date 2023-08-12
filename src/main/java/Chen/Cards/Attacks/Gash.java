package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.DoublePowerAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static Chen.ChenMod.makeID;

public class Gash extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Gash",
            2,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 12;
    private final static int UPG_COST = 1;

    public Gash()
    {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setCostUpgrade(UPG_COST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        if (m != null)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY)));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));

            if (m.hasPower(Hemorrhage.POWER_ID))
            {
                AbstractDungeon.actionManager.addToBottom(new DoublePowerAction(m, m.getPower(Hemorrhage.POWER_ID)));
            }
        }
    }
}