package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;
import static Chen.ChenMod.shiftsThisTurn;

public class EvasiveFireShift extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EvasiveFireShift",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 8;
    private final static int UPG_DAMAGE_A = 0;
    private final static int UPG_DAMAGE_B = 2;

    private final static int BLOCK_A = 6;
    private final static int BLOCK_B = 6;
    private final static int UPG_BLOCK_A = 2;
    private final static int UPG_BLOCK_B = 2;

    public EvasiveFireShift() {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setBlock(BLOCK_A, BLOCK_B, UPG_BLOCK_A, UPG_BLOCK_B);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (shiftsThisTurn > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }

        if (Form) //human
        {
            if (TwoFormFields.getForm())
                AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));


            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        else //cat
        {
            if (!TwoFormFields.getForm()) {
                AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
            }

            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }
}