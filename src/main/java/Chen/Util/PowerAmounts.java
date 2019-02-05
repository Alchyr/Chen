package Chen.Util;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class PowerAmounts {
    public static int getPowerAmount(AbstractCreature owner, String powerID)
    {
        if (owner == null)
        {
            return 0;
        }
        else
        {
            if (owner.hasPower(powerID))
            {
                return owner.getPower(powerID).amount;
            }
            return 0;
        }
    }
}
