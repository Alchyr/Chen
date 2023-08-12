package Chen.Interfaces;

import java.util.ArrayList;
import java.util.List;

public interface SpellCard {
    List<String> spellDescriptor = new ArrayList<>();

    SpellCard getCopyAsSpellCard();
}
