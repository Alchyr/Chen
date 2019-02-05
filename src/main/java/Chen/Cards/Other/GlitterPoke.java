package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Effects.DazzleEffect;
import Chen.Powers.Disoriented;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class GlitterPoke extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "GlitterPoke",
            0,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.BASIC
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 3;

    private final static int DEBUFF_A = 1;
    private final static int DEBUFF_B = 2;

    private final static int DRAW = 1;

    public GlitterPoke()
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY, true);

        setDamage(DAMAGE_A, DAMAGE_B);
        setMagic(DEBUFF_A, DEBUFF_B);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DazzleEffect(m.hb.cX, m.hb.cY)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.magicNumber), this.magicNumber));
        }
        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
        }
    }
}