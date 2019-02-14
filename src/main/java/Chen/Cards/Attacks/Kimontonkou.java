package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.KimontonkouAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.PerformXAction;
import Chen.Character.Chen;
import Chen.ChenMod;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Chen.ChenMod.makeID;

public class Kimontonkou extends BaseCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Kimontonkou",
            -1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int HITS = 0;
    private final static int UPG_HITS = 2;

    public Kimontonkou()
    {
        super(cardInfo, false);

        setMagic(HITS, UPG_HITS);

        this.isMultiDamage = true;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = ChenMod.spellsThisCombat;
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Kimontonkou();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        int baseValue = this.energyOnUse + SpellDamage.getSpellDamage(this);

        KimontonkouAction kimontonkouAction = new KimontonkouAction(p, this.multiDamage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new PerformXAction(kimontonkouAction, p, baseValue, this.freeToPlayOnce));
    }
}