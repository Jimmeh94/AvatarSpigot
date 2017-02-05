package avatar.manager;

import avatar.util.misc.BlockReplacement;

public class BlockManager extends Manager<BlockReplacement> {

    public void tick(){
        for(BlockReplacement replacement: objects){
            if(replacement.shouldReplace()){
                replacement.replace();
                remove(replacement);
            }
        }
    }

    public void replaceAll(){
        objects.forEach(BlockReplacement::replace);
    }

}
