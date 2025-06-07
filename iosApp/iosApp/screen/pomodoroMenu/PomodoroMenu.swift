import SwiftUI
import ComposableArchitecture

struct PomodoroMenu: View {
    let store: StoreOf<PomodoroFeature>
    
    var body: some View {
        NavigationStack {
            WithViewStore(
                store,
                observe: { $0 },
                content: { viewStore in
                    PomodoroRoute(
                        onSuccess: {
                            viewStore.send(.timerCompleted)
                        },
                        onFail: {
                            viewStore.send(.timerFailed)
                        },
                        viewStore: viewStore
                    )
                        .navigationDestination(
                            isPresented: Binding(
                                get: { viewStore.isPresented },
                                set: { isPresented in
                                    if !isPresented {
                                        viewStore.send(.routeDismissed)
                                    }
                                }
                            ),
                            destination: {
                                switch viewStore.route {
                                case .success: SuccessFocusScreen()
                                case .fail: FailFocusScreen()
                                case .none: EmptyView()
                                }
                            }
                        )
                }
            )
        }
        
    }
}
