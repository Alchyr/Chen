package Chen.Abstracts;

import Chen.ChenMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public abstract class TwoFormCharacter extends CustomPlayer {
    public boolean Form; //true = A, false = B

    private final static boolean DEFAULT_FORM = true;

    protected AbstractAnimation animationA;
    protected AbstractAnimation animationB;

    protected Color colorA;
    protected Color colorB;

    protected AbstractRoom currentRoom;

    public static int shiftsThisTurn = 0;

    public TwoFormCharacter(String name, PlayerClass playerClass,
                            String[] orbTexturesA, String orbVfxPathA, float[] layerSpeedsA,
                            AbstractAnimation animationA, AbstractAnimation animationB,
                            Color colorA, Color colorB) {
        super(name, playerClass, new CustomEnergyOrb(orbTexturesA, orbVfxPathA, layerSpeedsA), animationA);

        this.animationA = animationA;
        this.animationB = animationB;

        this.colorA = colorA.cpy();
        this.colorB = colorB.cpy();

        this.Form = DEFAULT_FORM;

        currentRoom = null;
    }

    public void Shift()
    {
        Shift(!this.Form);
    }
    public void Shift(boolean Form)
    {
        if (!this.animation.equals(Form ? animationA : animationB))
        {
            this.animation = Form ? animationA : animationB;
        }
        if (this.Form != Form)
        {
            shiftsThisTurn++;
            this.Form = Form;
        }
    }

    public void ShiftDefault()
    {
        this.Shift(DEFAULT_FORM);
    }

    @Override
    public void update() {
        super.update();
        if (currentRoom == null || !(currentRoom.equals(AbstractDungeon.getCurrRoom())))
        {
            currentRoom = AbstractDungeon.getCurrRoom();
            ChenMod.logger.info("Room changed - Updating form.");
            Shift(Form);
        }
    }


    @Override
    public void onVictory() {
        super.onVictory();
        this.Form = DEFAULT_FORM;
        shiftsThisTurn = 0;
        powers.clear();
    }

    @Override
    public void applyStartOfCombatLogic() {
        super.applyStartOfCombatLogic();
    }

    @Override
    public void applyStartOfCombatPreDrawLogic() {
        super.applyStartOfCombatPreDrawLogic();
    }

    @Override
    public void applyStartOfTurnRelics() {
        shiftsThisTurn = 0;
        super.applyStartOfTurnRelics();
    }

    @Override
    public Color getCardTrailColor() {
        if (Form)
            return colorA;
        return colorB;
    }
    @Override
    public Color getCardRenderColor() {
        if (Form)
            return colorA;
        return colorB;
    }
    @Override
    public Color getSlashAttackColor() {
        if (Form)
            return colorA;
        return colorB;
    }
}
