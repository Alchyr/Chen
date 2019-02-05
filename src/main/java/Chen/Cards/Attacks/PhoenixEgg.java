package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.PhoenixEggAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.SwordBoomerang;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class PhoenixEgg extends BaseCard implements SpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "PhoenixEgg",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 5;
    private final static int UPG_DAMAGE = 1;

    private final static int HITS = 2;

    public PhoenixEgg()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        setMagic(HITS);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new PhoenixEgg();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (SpellDamage.getSpellDamage(this) > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new PhoenixEggAction(p, new DamageInfo(p, this.baseDamage, this.damageTypeForTurn), SpellDamage.getSpellDamage(this)));
        }
    }
}