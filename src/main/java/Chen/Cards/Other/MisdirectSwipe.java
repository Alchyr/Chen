package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Character.Chen;
import Chen.Powers.Disoriented;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScrapeEffect;

import static Chen.ChenMod.makeID;

public class MisdirectSwipe extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MisdirectSwipe",
            2,
            CardType.SKILL,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 14;
    private final static int UPG_DAMAGE_A = 0;
    private final static int UPG_DAMAGE_B = 4;

    private final static int DEBUFF_A = 1;
    private final static int DEBUFF_B = 5;
    private final static int UPG_DEBUFF_A = 1;
    private final static int UPG_DEBUFF_B = 1;

    private final static int BLOCK_A = 10;
    private final static int BLOCK_B = 0;
    private final static int UPG_BLOCK_A = 6;
    private final static int UPG_BLOCK_B = 0;

    public MisdirectSwipe()
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setBlock(BLOCK_A, BLOCK_B, UPG_BLOCK_A, UPG_BLOCK_B);
        setMagic(DEBUFF_A, DEBUFF_B, UPG_DEBUFF_A, UPG_DEBUFF_B);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            if (m != null)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ScrapeEffect(m.hb.cX, m.hb.cY)));
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Hemorrhage(m, p, this.magicNumber), this.magicNumber));
        }
    }
}