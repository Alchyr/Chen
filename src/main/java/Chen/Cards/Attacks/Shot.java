package Chen.Cards.Attacks;

import Chen.Abstracts.StandardSpell;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Actions.GenericActions.AlwaysDamageRandomEnemyAction;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class Shot extends StandardSpell {
    private final static CardInfo cardInfo = new CardInfo(
            "Shot",
            1,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.COMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 4;
    private final static int UPG_DAMAGE = 1;

    public Shot()
    {
        super(cardInfo, false);

        setDamage(DAMAGE,  UPG_DAMAGE);
    }

    @Override
    public StandardSpell getCopyAsSpellCard() {
        return new Shot();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new AlwaysDamageRandomEnemyAction(new DamageInfo(p, damage * 2, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}