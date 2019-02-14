package Chen.Cards.Attacks;

import Chen.Abstracts.BaseCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Effects.RandomFastSliceEffect;
import Chen.Powers.Disoriented;
import Chen.Util.CardInfo;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Chen.ChenMod.makeID;

public class SurpriseAttack extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "SurpriseAttack",
            0,
            CardType.ATTACK,
            CardTarget.ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 5;
    private final static int UPG_DAMAGE = 3;

    private final static int DEBUFF = 1;

    public SurpriseAttack()
    {
        super(cardInfo, false);

        setInnate(true, true);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DEBUFF);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p instanceof TwoFormCharacter && ((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenCat));
        }

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new RandomFastSliceEffect(m.hb.cX, m.hb.cY, Color.RED, Color.BLACK)));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.NONE));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new Disoriented(m, this.magicNumber), this.magicNumber));
    }
}