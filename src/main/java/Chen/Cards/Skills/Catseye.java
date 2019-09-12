package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Powers.CatseyePower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Catseye extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Catseye",
            1,
            CardType.SKILL,
            CardTarget.SELF,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int UPG_COST = 0;
    private final static int BUFF = 1;

    public Catseye()
    {
        super(cardInfo, false);

        setCostUpgrade(UPG_COST);
        setMagic(BUFF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CatseyePower(p, this.magicNumber), this.magicNumber));
    }
}