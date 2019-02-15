package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Powers.Hemorrhage;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.Dodecahedron;
import com.megacrit.cardcrawl.relics.Lantern;
import com.megacrit.cardcrawl.relics.NinjaScroll;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Chen.ChenMod.makeID;

public class Assault extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Assault",
            2,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 12;
    private final static int UPG_DAMAGE = 2;
    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 1;

    public Assault()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);

        this.isMultiDamage = true;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_WHIRLWIND"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new Hemorrhage(mo, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
}