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
                    PomodoroRoute(viewStore: viewStore)
                        .navigationDestination(
                            isPresented: Binding(
                                get: { viewStore.route != nil },
                                set: { isPresented in
                                    if !isPresented {
                                        viewStore.send(.routeDismissed)
                                    }
                                }
                            )
                        ) {
                            switch viewStore.route {
                            case .success: SuccessFocusScreen(
                                onFinish: { viewStore.send(.tabBackToMainMenu) },
                                onBreak: { viewStore.send(.tapBreak) }
                            )
                            case .fail: FailFocusScreen()
                            case .none: EmptyView()
                            }
                        }
                    
                }
            )
        }
        
    }
}
