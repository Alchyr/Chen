package Chen.Cards.Attacks;

import Chen.Abstracts.DamageSpellCard;
import Chen.Effects.SalvoEffect;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Chen.ChenMod.makeID;

public class Salvo extends DamageSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Salvo",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 8;
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
        int salvoCount = this.baseMagicNumber + 1;
        if (Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SalvoEffect(salvoCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SalvoEffect(salvoCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        if (this.baseMagicNumber > 0)
        {
            Salvo copyCard = (Salvo)this.makeStatEquivalentCopy();

            copyCard.magicNumber = copyCard.baseMagicNumber = this.baseMagicNumber / 2;

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copyCard));
        }
    }
}