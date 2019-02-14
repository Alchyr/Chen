package Chen.Cards.Attacks;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.GenericActions.CheckDebuffCycleAction;
import Chen.Powers.Disoriented;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class UpperHand extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "UpperHand",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 8;
    private final static int DAMAGE_B = 4;
    private final static int UPG_DAMAGE_A = 2;
    private final static int UPG_DAMAGE_B = 1;
    private final static int MAGIC = 1;

    public UpperHand() {
        super(cardInfo, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setMagic(MAGIC);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new CheckDebuffCycleAction(m, Disoriented.POWER_ID, this.magicNumber, 1));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SMASH));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new CheckDebuffCycleAction(m, Hemorrhage.POWER_ID, this.magicNumber, 1));

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }
}