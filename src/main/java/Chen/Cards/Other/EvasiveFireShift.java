package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.BlockSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;
import static Chen.ChenMod.shiftsThisTurn;

public class EvasiveFireShift extends ShiftChenCard implements BlockSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "EvasiveFireShift",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 7;
    private final static int UPG_DAMAGE_A = 0;
    private final static int UPG_DAMAGE_B = 2;

    private final static int MAGIC_A = 7;
    private final static int MAGIC_B = 7;
    private final static int UPG_MAGIC_A = 2;
    private final static int UPG_MAGIC_B = 2;

    public EvasiveFireShift() {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setMagic(MAGIC_A, MAGIC_B, UPG_MAGIC_A, UPG_MAGIC_B);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new EvasiveFireShift();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            if (p instanceof TwoFormCharacter) {
                if (((TwoFormCharacter) p).Form)
                    AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
            }

            if (shiftsThisTurn > 0)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, SpellDamage.getSpellDamage(this)));
            }
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, SpellDamage.getSpellDamage(this)));
        }
        else //cat
        {
            if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
                AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
            }


            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, SpellDamage.getSpellDamage(this)));
            if (shiftsThisTurn > 0 && m != null)
            {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
}