package Chen.Cards.Attacks;

import Chen.Abstracts.DamageSpellCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.AlwaysDamageRandomEnemyAction;
import Chen.Character.Chen;
import Chen.Interfaces.SpellCard;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import Chen.Variables.SpellDamage;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Shot extends DamageSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Shot",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 3;
    private final static int UPG_DAMAGE = 1;

    public Shot()
    {
        super(cardInfo, false);

        setMagic(DAMAGE,  UPG_DAMAGE);
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Shot();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, SpellDamage.getSpellDamage(this), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new AlwaysDamageRandomEnemyAction(new DamageInfo(p, SpellDamage.getSpellDamage(this) * 2, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}