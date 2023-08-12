package Chen.Cards.Attacks;

import Chen.Abstracts.StandardSpell;
import Chen.Actions.ChenActions.PhoenixEggAction;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.List;

import static Chen.ChenMod.makeID;

public class PhoenixEgg extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "PhoenixEgg",
            1,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.RARE
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 1;

    private final static int HITS = 3;

    public PhoenixEgg()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
        setMagic(HITS);
        magicNumSpell();
    }

    @Override
    public List<String> getCardDescriptors() {
        return SpellCard.spellDescriptor;
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new PhoenixEgg();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (this.magicNumber > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new PhoenixEggAction(p, this, this.magicNumber));
        }
    }
}