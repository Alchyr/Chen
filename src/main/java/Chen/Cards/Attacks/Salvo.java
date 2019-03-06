package Chen.Cards.Attacks;

import Chen.Abstracts.DamageSpellCard;
import Chen.Abstracts.TwoFormCharacter;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Character.Chen;
import Chen.Effects.SalvoEffect;
import Chen.Interfaces.SpellCard;
import Chen.Util.CardInfo;
import com.badlogic.gdx.Game;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Chen.ChenMod.makeID;

public class Salvo extends DamageSpellCard {
    private final static CardInfo cardInfo = new CardInfo(
            "Salvo",
            0,
            CardType.ATTACK,
            CardTarget.ALL_ENEMY,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    private final static int DAMAGE = 5;
    private final static int UPG_DAMAGE = 2;

    private int timesPlayedThisTurn;
    private int playedTurn;

    public Salvo()
    {
        super(cardInfo, false);

        setMagic(DAMAGE,  UPG_DAMAGE);
        setExhaust(true);

        this.isMultiDamage = true;
        timesPlayedThisTurn = 0;
        playedTurn = 0;
    }

    @Override
    public SpellCard getCopyAsSpellCard() {
        return new Salvo();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        updateCost(-999);
        timesPlayedThisTurn = 0;
    }

    @Override
    public void triggerWhenDrawn() {
        if (playedTurn != GameActionManager.turn && timesPlayedThisTurn > 0) //wasn't reset properly
        {
            updateCost(-999);
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int salvoCount = this.baseMagicNumber + 1;
        if (p instanceof TwoFormCharacter && !((TwoFormCharacter) p).Form) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        if (Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SalvoEffect(salvoCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SalvoEffect(salvoCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));

        Salvo copyCard = (Salvo)this.makeStatEquivalentCopy();

        copyCard.updateCost(1);
        copyCard.timesPlayedThisTurn = this.timesPlayedThisTurn + 1;
        copyCard.playedTurn = GameActionManager.turn;

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copyCard));
    }
}