package Chen.Abstracts;

import Chen.ChenMod;
import Chen.Util.CardInfo;
import com.brashmonkey.spriter.Player;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class SwiftCard extends BaseCard {
    public SwiftCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }

    public SwiftCard(String cardName, int cost, CardType cardType, CardTarget target, CardRarity rarity, boolean upgradesDescription) {
        super(cardName, cost, cardType, target, rarity, upgradesDescription);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (ChenMod.shiftsThisTurn > 0)
        {
            this.modifyCostForTurn(-10000); //hopefully nothing ever costs more than 10000 energy
        }
    }
}
