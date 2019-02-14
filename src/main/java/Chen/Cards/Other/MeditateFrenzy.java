package Chen.Cards.Other;

import Chen.Abstracts.ShiftChenCard;
import Chen.Effects.RandomClawEffect;
import Chen.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static Chen.ChenMod.makeID;

public class MeditateFrenzy extends ShiftChenCard {
    private final static CardInfo cardInfo = new CardInfo(
            "MeditateFrenzy",
            2,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE_A = 0;
    private final static int DAMAGE_B = 3;
    private final static int UPG_DAMAGE_A = 0;
    private final static int UPG_DAMAGE_B = 1;

    private final static int BLOCK_A = 14;
    private final static int BLOCK_B = 0;
    private final static int UPG_BLOCK_A = 4;
    private final static int UPG_BLOCK_B = 0;

    private final static int MAGIC_A = 1;
    private final static int MAGIC_B = 1;
    private final static int UPG_MAGIC_A = 1;
    private final static int UPG_MAGIC_B = 1;

    private final static int HITS = 6;

    public MeditateFrenzy()
    {
        super(cardInfo, CardType.ATTACK, CardTarget.ENEMY, false);

        setDamage(DAMAGE_A, DAMAGE_B, UPG_DAMAGE_A, UPG_DAMAGE_B);
        setBlock(BLOCK_A, BLOCK_B, UPG_BLOCK_A, UPG_BLOCK_B);
        setMagic(MAGIC_A, MAGIC_B, UPG_MAGIC_A, UPG_MAGIC_B);

        setExhaust(true, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Form) //human
        {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FocusPower(p, this.magicNumber), this.magicNumber));
        }
        else //cat
        {
            for (int i = 0; i < HITS; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new RandomClawEffect(m.hb.cX, m.hb.cY, Color.RED, Color.BLACK)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            }

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }
}