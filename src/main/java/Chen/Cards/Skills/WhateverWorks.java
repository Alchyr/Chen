package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.WhateverAction;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class WhateverWorks extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "WhateverWorks",
            3,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    public final static int REDUCTION = 1;

    public WhateverWorks()
    {
        super(cardInfo, true);
        setMagic(REDUCTION);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new WhateverAction(p, this.upgraded, this.magicNumber));
    }
}