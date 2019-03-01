package Chen.Cards.Attacks;

import Chen.Abstracts.SwiftCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Cleave;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Chen.ChenMod.makeID;

public class Sweep extends SwiftCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Sweep",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 3;

    private final static int HITS = 2;
    private final static int UPG_HITS = 1;

    public Sweep()
    {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setMagic(HITS, UPG_HITS);

        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect()));
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }
}