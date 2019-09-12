package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Scratch extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Scratch",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 1;

    private final static int DEBUFF = 2;
    private final static int UPG_DEBUFF = 1;

    public Scratch()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.magicNumber), this.magicNumber));
    }
}