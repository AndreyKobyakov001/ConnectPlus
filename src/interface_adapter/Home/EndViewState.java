package interface_adapter.Home;

public class EndViewState {

        private int isWon = -1;
        private int ELODelta = 0;

        public EndViewState() {}

        public EndViewState(EndViewState copy) {
            isWon = copy.isWon;
            ELODelta = copy.ELODelta;
        }

        public void setIsWon(int isWon) {
            this.isWon = isWon;
        }

        public int getIsWon() {
            return isWon;
        }

        public void setELODelta(int ELODelta) {
            this.ELODelta = ELODelta;
        }

        public int getELODelta() {
            return ELODelta;
        }


}
