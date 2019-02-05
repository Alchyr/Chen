package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.DamageSpellCard;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Util.PowerAmounts;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Chen.ChenMod.makeID;

public class Salvo extends BaseCard implements DamageSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Salvo",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 7;
    private final static int UPG_DAMAGE = 2;

    public Salvo()
    {
        super(cardInfo, false);

        setMagic(DAMAGE,  UPG_DAMAGE);
        setExhaust(true);

        this.isMultiDamage = true;
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Salvo();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlizzardEffect(2, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlizzardEffect(2, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, SpellDamage.getSpellDamage(this), this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }
}