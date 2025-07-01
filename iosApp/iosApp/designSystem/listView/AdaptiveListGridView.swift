import SwiftUI

struct AdaptiveListGridView<Content: View, T: Identifiable>: View {
	@Environment(\.horizontalSizeClass) private var horizontalSizeClass
	
	private let items: [T]
	private let gridMinSize: CGFloat
	private let spacing: CGFloat
	private let content: (T) -> Content
	
	init(
		items: [T],
		gridMinSize: CGFloat = 360,
		spacing: CGFloat = 16,
		@ViewBuilder content: @escaping (T) -> Content
	) {
		self.items = items
		self.gridMinSize = gridMinSize
		self.spacing = spacing
		self.content = content
	}
	
	private var isGrid: Bool {
		horizontalSizeClass == .regular
	}
	
	private var gridSize: [GridItem] {
		[GridItem(.adaptive(minimum: gridMinSize), spacing: spacing)]
	}
	
	var body: some View {
		ScrollView {
			if isGrid {
				LazyVGrid(columns: gridSize, spacing: spacing) {
					ListView()
				}
			} else {
				LazyVStack(spacing: spacing) {
					ListView()
				}
			}
		}
		.scrollIndicators(.hidden)
	}
	
	@ViewBuilder
	private func ListView() -> some View {
		ForEach(items) { item in
			content(item)
		}
	}
}
