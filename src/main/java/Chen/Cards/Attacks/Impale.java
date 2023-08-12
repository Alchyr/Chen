package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ImpaleAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.PerformXAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Chen.ChenMod.makeID;

public class Impale extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Impale",
            -1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 6;
    private final static int UPG_DAMAGE = 2;

    private final static int DEBUFF = 3;
    private final static int UPG_DEBUFF = 1;

    public Impale()
    {
        super(cardInfo, false);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF, UPG_DEBUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        ImpaleAction impaleAction = new ImpaleAction(p, m, this.magicNumber, this.damage, this.damageTypeForTurn);
        AbstractDungeon.actionManager.addToBottom(new PerformXAction(impaleAction, p, this.energyOnUse, this.freeToPlayOnce));
    }
}