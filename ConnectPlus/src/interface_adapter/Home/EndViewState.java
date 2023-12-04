package interface_adapter.Home;

public class EndViewState {

        private int isWon;
        private int ELODelta;

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
