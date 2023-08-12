package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Powers.DamageUpPower;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class FlankingStrike extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "FlankingStrike",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 7;
    private final static int UPG_COST = 0;

    public FlankingStrike()
    {
        super(cardInfo, false);

        setDamage(DAMAGE);
        setCostUpgrade(UPG_COST);

        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DamageUpPower(p, 1), 1));
    }
}