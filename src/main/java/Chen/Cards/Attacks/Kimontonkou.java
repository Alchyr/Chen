package Chen.Cards.Attacks;

import Chen.Abstracts.StandardSpell;
import Chen.Actions.ChenActions.KimontonkouAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.PerformXAction;
import Chen.Character.Chen;
import Chen.ChenMod;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Chen.ChenMod.makeID;

public class Kimontonkou extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Kimontonkou",
            -1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int HITS = 0;
    private final static int UPG_HITS = 1;

    public Kimontonkou()
    {
        super(cardInfo, true);

        setDamage(0);
        setMagic(HITS, UPG_HITS);

        this.isMultiDamage = true;
    }

    @Override
    public StandardSpell getCopyAsSpellCard() {
        return new Kimontonkou();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = ChenMod.spellsThisCombat;
        super.applyPowers();
        this.rawDescription = (upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.baseDamage = ChenMod.spellsThisCombat;
        super.calculateCardDamage(mo);
        this.rawDescription = (upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION) + cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        int baseValue = this.energyOnUse + this.magicNumber;

        KimontonkouAction kimontonkouAction = new KimontonkouAction(p, this.multiDamage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new PerformXAction(kimontonkouAction, p, baseValue, this.freeToPlayOnce));
    }
}